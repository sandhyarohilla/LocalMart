# 🚀 Deployment Guide for LocalMart

This guide will help you deploy your LocalMart application to production.

## 📋 Table of Contents
1. [Quick Deploy Options](#quick-deploy-options)
2. [Backend Deployment](#backend-deployment)
3. [Frontend Deployment](#frontend-deployment)
4. [Environment Variables](#environment-variables)
5. [Post-Deployment](#post-deployment)

---

## 🎯 Quick Deploy Options

### Option 1: Railway (Recommended - Easiest)
- **Backend**: Railway (Free tier available)
- **Frontend**: Vercel or Netlify (Free)

### Option 2: Render
- **Backend**: Render (Free tier available)
- **Frontend**: Render Static Sites (Free)

### Option 3: Vercel + Railway
- **Backend**: Railway
- **Frontend**: Vercel (Excellent for React)

---

## 🔧 Backend Deployment

### Method 1: Railway (Recommended)

1. **Sign up at [Railway.app](https://railway.app)**

2. **Create New Project**
   - Click "New Project"
   - Select "Deploy from GitHub repo"
   - Choose your `LocalMart` repository
   - Railway will auto-detect it's a Java project

3. **Configure Environment Variables**
   - Go to your service → Variables
   - Add these variables:
     ```
     SPRING_PROFILES_ACTIVE=prod
     JWT_SECRET=your-secret-key-here (generate a random 64-character hex string)
     FRONTEND_URL=https://your-frontend-url.vercel.app
     PORT=8080
     ```

4. **Deploy**
   - Railway will automatically build and deploy
   - Your backend will be live at: `https://your-app.railway.app`

5. **Get Your Backend URL**
   - Copy the URL from Railway dashboard
   - You'll need this for frontend configuration

---

### Method 2: Render

1. **Sign up at [Render.com](https://render.com)**

2. **Create New Web Service**
   - Connect your GitHub repository
   - Select the repository
   - Choose "Web Service"

3. **Configure Build Settings**
   - **Build Command**: `cd backend && ./mvnw clean package -DskipTests`
   - **Start Command**: `cd backend && java -jar target/backend-0.0.1-SNAPSHOT.jar`
   - **Environment**: Java

4. **Add Environment Variables**
   ```
   SPRING_PROFILES_ACTIVE=prod
   JWT_SECRET=your-secret-key-here
   FRONTEND_URL=https://your-frontend-url.vercel.app
   ```

5. **Deploy**
   - Click "Create Web Service"
   - Render will build and deploy automatically

---

## 🎨 Frontend Deployment

### Method 1: Vercel (Recommended)

1. **Sign up at [Vercel.com](https://vercel.com)**

2. **Import Project**
   - Click "Add New Project"
   - Import your GitHub repository
   - Vercel will auto-detect it's a Vite project

3. **Configure Build Settings**
   - **Framework Preset**: Vite
   - **Root Directory**: `frontend`
   - **Build Command**: `npm run build`
   - **Output Directory**: `dist`

4. **Add Environment Variables**
   - Go to Project Settings → Environment Variables
   - Add:
     ```
     VITE_API_URL=https://your-backend-url.railway.app
     ```
   - Replace with your actual backend URL

5. **Deploy**
   - Click "Deploy"
   - Your frontend will be live at: `https://your-app.vercel.app`

---

### Method 2: Netlify

1. **Sign up at [Netlify.com](https://netlify.com)**

2. **Add New Site**
   - Choose "Import from Git"
   - Connect GitHub and select repository

3. **Configure Build Settings**
   - **Base directory**: `frontend`
   - **Build command**: `npm run build`
   - **Publish directory**: `frontend/dist`

4. **Add Environment Variables**
   - Go to Site Settings → Environment Variables
   - Add:
     ```
     VITE_API_URL=https://your-backend-url.railway.app
     ```

5. **Deploy**
   - Click "Deploy site"
   - Your frontend will be live

---

## 🔐 Environment Variables

### Backend Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `SPRING_PROFILES_ACTIVE` | Spring profile | `prod` |
| `JWT_SECRET` | Secret key for JWT tokens | `6B5267556A586E3272357538782F413F4428472B4B6250645367566B59703373` |
| `FRONTEND_URL` | Frontend URL for CORS | `https://localmart.vercel.app` |
| `PORT` | Server port | `8080` |
| `DATABASE_URL` | Database connection (if using external DB) | `jdbc:h2:file:./data/localmart-db` |

### Frontend Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `VITE_API_URL` | Backend API URL | `https://localmart-backend.railway.app` |

---

## 📝 Step-by-Step Deployment

### Step 1: Deploy Backend

1. Choose Railway or Render
2. Connect GitHub repository
3. Set environment variables
4. Deploy and get backend URL

### Step 2: Deploy Frontend

1. Choose Vercel or Netlify
2. Connect GitHub repository
3. Set `VITE_API_URL` to your backend URL
4. Deploy and get frontend URL

### Step 3: Update CORS

1. Go back to backend environment variables
2. Update `FRONTEND_URL` with your frontend URL
3. Redeploy backend

---

## ✅ Post-Deployment Checklist

- [ ] Backend is accessible (test: `https://your-backend.railway.app/api/products`)
- [ ] Frontend is accessible
- [ ] CORS is configured correctly
- [ ] Environment variables are set
- [ ] Test user registration
- [ ] Test product creation
- [ ] Test order placement

---

## 🐛 Troubleshooting

### Backend Issues

**Problem**: CORS errors
- **Solution**: Make sure `FRONTEND_URL` is set correctly in backend env vars

**Problem**: Database not persisting
- **Solution**: H2 file database works, but for production consider PostgreSQL

**Problem**: File uploads not working
- **Solution**: Check `UPLOAD_DIR` environment variable

### Frontend Issues

**Problem**: API calls failing
- **Solution**: Check `VITE_API_URL` is set correctly

**Problem**: Build fails
- **Solution**: Make sure you're in the `frontend` directory for build

---

## 🔄 Updating Your Deployment

After making changes:

1. **Commit and push to GitHub**
   ```bash
   git add .
   git commit -m "Your changes"
   git push
   ```

2. **Automatic Deployment**
   - Railway/Render will auto-deploy backend
   - Vercel/Netlify will auto-deploy frontend

---

## 💡 Pro Tips

1. **Use PostgreSQL for Production**: H2 is fine for development, but use PostgreSQL for production
2. **Use Cloud Storage**: For file uploads, use AWS S3 or Cloudinary instead of local storage
3. **Enable HTTPS**: All platforms provide HTTPS by default
4. **Monitor Logs**: Check platform logs if something goes wrong
5. **Set up Custom Domain**: Add your own domain for a professional look

---

## 📞 Need Help?

If you encounter issues:
1. Check platform logs
2. Verify environment variables
3. Test API endpoints directly
4. Check CORS configuration

Good luck with your deployment! 🚀

