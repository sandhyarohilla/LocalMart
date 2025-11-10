// src/pages/SellerListPage.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/layout/Header';
import Footer from '../components/layout/Footer';
import SellerList from '../components/sellers/SellerList';
import styles from './SellerListPage.module.css';

const API_URL = 'http://localhost:8080/api/sellers';

export default function SellerListPage() {
  const [sellers, setSellers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchSellers = async () => {
      setLoading(true);
      setError(null);
      try {
        const response = await axios.get(API_URL);
        setSellers(response.data);
      } catch (err) {
        setError('Failed to fetch sellers. Please try again.');
        console.error(err);
      }
      setLoading(false);
    };

    fetchSellers();
  }, []);

  return (
    <>
      <Header />
      <main className={styles.page}>
        <div className={styles.container}>
          <div className={styles.header}>
            <h1 className={styles.title}>Browse All Sellers</h1>
            <p className={styles.subtitle}>
              Discover local sellers and their amazing products
            </p>
          </div>

          {loading && (
            <div className={styles.message}>
              <p>Loading sellers...</p>
            </div>
          )}

          {error && (
            <div className={styles.errorMessage}>
              <p>{error}</p>
            </div>
          )}

          {!loading && !error && sellers.length === 0 && (
            <div className={styles.message}>
              <p>No sellers have registered yet.</p>
            </div>
          )}

          {!loading && !error && sellers.length > 0 && (
            <SellerList sellers={sellers} />
          )}
        </div>
      </main>
      <Footer />
    </>
  );
}