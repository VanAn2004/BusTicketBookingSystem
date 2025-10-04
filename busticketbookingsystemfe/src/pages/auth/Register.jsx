import React, { useState } from "react";
import axios from "axios";

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    userName: "",
    password: "",
    email: "",
    phone: "",
    roleName: "CUSTOMER"  // mặc định CUSTOMER
  });

  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");

    try {
      const res = await axios.post(
        "http://localhost:8080/api/v1/users/registration",
        formData,
        { headers: { "Content-Type": "application/json" } }
      );
      setSuccess("Đăng ký thành công!");
      console.log("Đăng ký thành công:", res.data);
    } catch (err) {
      console.error("Đăng ký thất bại:", err.response?.data || err.message);
      setError(
        err.response?.data?.message || "Có lỗi xảy ra. Vui lòng thử lại!"
      );
    }
  };

  return (
    <div style={{ maxWidth: "400px", margin: "50px auto" }}>
      <h2>Đăng ký</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="firstName"
          placeholder="Họ"
          value={formData.firstName}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="text"
          name="lastName"
          placeholder="Tên"
          value={formData.lastName}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="text"
          name="userName"
          placeholder="Tên đăng nhập"
          value={formData.userName}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="password"
          name="password"
          placeholder="Mật khẩu"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="email"
          name="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <br />
        <input
          type="text"
          name="phone"
          placeholder="Số điện thoại"
          value={formData.phone}
          onChange={handleChange}
          required
        />
        <br />

        <button type="submit">Đăng ký</button>
      </form>

      {success && <p style={{ color: "green" }}>{success}</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
};

export default Register;
