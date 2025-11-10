// src/components/sellers/SellerList.jsx
import React from 'react';
import { Link } from 'react-router-dom';
import { Store, MapPin, Mail, ArrowRight } from 'lucide-react';
import styles from './SellerList.module.css';

export default function SellerList({ sellers }) {
  return (
    <div className={styles.sellerGrid}>
      {sellers.map((seller) => (
        <div key={seller.sellerId} className={styles.sellerCard}>
          <div className={styles.cardHeader}>
            <div className={styles.storeIcon}>
              <Store width={32} height={32} />
            </div>
            <div className={styles.storeInfo}>
              <h3 className={styles.storeName}>{seller.storeName}</h3>
              <p className={styles.ownerName}>Owner: {seller.ownerName}</p>
            </div>
          </div>
          
          <div className={styles.cardBody}>
            {seller.address && (
              <div className={styles.infoRow}>
                <MapPin width={16} height={16} />
                <span className={styles.infoText}>{seller.address}</span>
              </div>
            )}
            <div className={styles.infoRow}>
              <Mail width={16} height={16} />
              <span className={styles.infoText}>{seller.email}</span>
            </div>
          </div>
          
          <div className={styles.cardFooter}>
            <Link to={`/products?seller=${seller.sellerId}`} className={styles.viewProductsButton}>
              <span>View Products</span>
              <ArrowRight width={16} height={16} />
            </Link>
          </div>
        </div>
      ))}
    </div>
  );
}



