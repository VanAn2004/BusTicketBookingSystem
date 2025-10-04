// src/routes/AppRoutes.jsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Login from "../pages/auth/Login";
import Register from "../pages/auth/Register";
import Trips from "../pages/trip/TripList";
import MyBookings from "../pages/booking/MyBookings";
import ForgotPassword from "../pages/auth/ForgotPassword";   // ✅ thêm
import ResetPassword from "../pages/auth/ResetPassword";     // ✅ thêm

// Import Navbar và Footer
import Navbar from "../components/Navbar";
import Footer from "../components/Footer";

export default function AppRoutes() {
  return (
    <BrowserRouter>
      <div style={{ display: "flex", flexDirection: "column", minHeight: "100vh" }}>
        {/* Navbar luôn ở trên cùng */}
        <Navbar />

        {/* Nội dung trang */}
        <main style={{ flex: 1, padding: "20px" }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/trips" element={<Trips />} />
            <Route path="/bookings" element={<MyBookings />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            {/* ✅ Route mới */}
            <Route path="/forgot-password" element={<ForgotPassword />} />
            <Route path="/reset-password" element={<ResetPassword />} />
          </Routes>
        </main>

        {/* Footer luôn ở cuối */}
        <Footer />
      </div>
    </BrowserRouter>
  );
}
