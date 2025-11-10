// API Configuration
// Uses environment variable in production, localhost in development
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

export const API_ENDPOINTS = {
  AUTH: `${API_BASE_URL}/api/auth`,
  PRODUCTS: `${API_BASE_URL}/api/products`,
  SELLERS: `${API_BASE_URL}/api/sellers`,
  CART: `${API_BASE_URL}/api/cart`,
  ORDERS: `${API_BASE_URL}/api/orders`,
  CUSTOMER: `${API_BASE_URL}/api/customer`,
  DASHBOARD: `${API_BASE_URL}/api/dashboard`,
};

export default API_BASE_URL;

