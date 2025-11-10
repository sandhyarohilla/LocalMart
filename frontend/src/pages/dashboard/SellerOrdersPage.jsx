// src/pages/dashboard/SellerOrdersPage.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useAuth } from '../../context/AuthContext';
import { Eye, Check, X } from 'lucide-react';
import styles from './SellerOrdersPage.module.css';

const API_URL = 'http://localhost:8080/api/orders';

export default function SellerOrdersPage() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const [processingOrderId, setProcessingOrderId] = useState(null);
  const { token } = useAuth();

  const getAuthHeaders = () => ({
    headers: { 'Authorization': `Bearer ${token}` }
  });

  const fetchOrders = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get(`${API_URL}/seller`, getAuthHeaders());
      setOrders(response.data);
    } catch (err) {
      setError("Failed to fetch orders.");
      console.error(err);
    }
    setLoading(false);
  };

  useEffect(() => {
    if (token) {
      fetchOrders();
    }
  }, [token]);

  const handleApproveOrder = async (orderId) => {
    if (!window.confirm('Are you sure you want to approve this order?')) {
      return;
    }

    setProcessingOrderId(orderId);
    setError(null);
    setSuccessMessage(null);

    try {
      await axios.put(`${API_URL}/${orderId}/approve`, {}, getAuthHeaders());
      setSuccessMessage('Order approved successfully!');
      setTimeout(() => setSuccessMessage(null), 3000);
      // Refresh orders list
      await fetchOrders();
    } catch (err) {
      setError(err.response?.data?.message || err.response?.data || 'Failed to approve order.');
      console.error(err);
    } finally {
      setProcessingOrderId(null);
    }
  };

  const handleRejectOrder = async (orderId) => {
    if (!window.confirm('Are you sure you want to reject this order? Stock will be restored.')) {
      return;
    }

    setProcessingOrderId(orderId);
    setError(null);
    setSuccessMessage(null);

    try {
      await axios.put(`${API_URL}/${orderId}/reject`, {}, getAuthHeaders());
      setSuccessMessage('Order rejected successfully. Stock has been restored.');
      setTimeout(() => setSuccessMessage(null), 3000);
      // Refresh orders list
      await fetchOrders();
    } catch (err) {
      setError(err.response?.data?.message || err.response?.data || 'Failed to reject order.');
      console.error(err);
    } finally {
      setProcessingOrderId(null);
    }
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      month: 'short',
      day: 'numeric',
      year: 'numeric'
    });
  };

  return (
    <div className={styles.page}>
      <h1 className={styles.title}>Manage Orders</h1>
      
      {error && <div className={styles.errorMessage}>{error}</div>}
      {successMessage && <div className={styles.successMessage}>{successMessage}</div>}

      <div className={styles.tableWrapper}>
        <table className={styles.orderTable}>
          <thead>
            <tr>
              <th>Order ID</th>
              <th>Date</th>
              <th>Total</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr><td colSpan="5" className={styles.tableMessage}>Loading orders...</td></tr>
            ) : orders.length > 0 ? (
              orders.map((order) => (
                <tr key={order.orderId}>
                  <td className={styles.orderId}>#{order.orderId}</td>
                  <td>{formatDate(order.orderDate)}</td>
                  <td>₹{order.totalAmount.toFixed(2)}</td>
                  <td>
                    <span className={`${styles.status} ${styles[order.status.toLowerCase()]}`}>
                      {order.status}
                    </span>
                  </td>
                  <td>
                    {order.status === 'Pending' ? (
                      <div className={styles.actionButtons}>
                        <button 
                          className={`${styles.iconButton} ${styles.success}`} 
                          aria-label="Accept"
                          onClick={() => handleApproveOrder(order.orderId)}
                          disabled={processingOrderId === order.orderId}
                          title="Approve Order"
                        >
                          <Check width={16} height={16} />
                        </button>
                        <button 
                          className={`${styles.iconButton} ${styles.danger}`} 
                          aria-label="Reject"
                          onClick={() => handleRejectOrder(order.orderId)}
                          disabled={processingOrderId === order.orderId}
                          title="Reject Order"
                        >
                          <X width={16} height={16} />
                        </button>
                      </div>
                    ) : (
                      <button className={styles.iconButton} aria-label="View" title="View Order">
                        <Eye width={16} height={16} />
                      </button>
                    )}
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="5" className={styles.tableMessage}>You have not received any orders yet.</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}