import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";  // ✅ import Link
import { login } from "../../api/auth";
import "../../assets/css/Login.css";
import loginBg from "../../assets/img/login-bg.png";

export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await login({ userName: username, password });
      console.log("Login response:", res.data);
      localStorage.setItem("accessToken", res.data.result.token);
      navigate("/");
    } catch (err) {
      console.error("Login error:", err.response?.data || err.message);
      setError("Sai username hoặc password!");
    }
  };

  return (
    <div className="login">
      <img src={loginBg} alt="login background" className="login__img" />

      <form className="login__form" onSubmit={handleSubmit}>
        <h1 className="login__title">Login</h1>

        {error && <p style={{ color: "red", textAlign: "center" }}>{error}</p>}

        <div className="login__content">
          <div className="login__box">
            <i className="ri-user-3-line login__icon"></i>
            <div className="login__box-input">
              <input
                type="text"
                required
                className="login__input"
                id="login-username"
                placeholder=" "
                value={username}
                onChange={(e) => setUsername(e.target.value)}
              />
              <label htmlFor="login-username" className="login__label">
                Username
              </label>
            </div>
          </div>

          <div className="login__box">
            <i className="ri-lock-2-line login__icon"></i>
            <div className="login__box-input">
              <input
                type="password"
                required
                className="login__input"
                id="login-pass"
                placeholder=" "
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <label htmlFor="login-pass" className="login__label">
                Password
              </label>
            </div>
          </div>
        </div>

        <div className="login__check">
          <div className="login__check-group">
            <input type="checkbox" className="login__check-input" id="login-check" />
            <label htmlFor="login-check" className="login__check-label">
              Remember me
            </label>
          </div>
          {/* ✅ Forgot password link */}
          <Link to="/forgot-password" className="login__forgot">
            Forgot Password?
          </Link>
        </div>

        <button type="submit" className="login__button">Login</button>

        <p className="login__register">
          Don't have an account?{" "}
          <Link to="/register">Register</Link> {/* ✅ Dùng Link thay vì a */}
        </p>
      </form>
    </div>
  );
}
