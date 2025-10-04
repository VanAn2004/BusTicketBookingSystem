import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
  const token = localStorage.getItem("accessToken");
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    navigate("/login");
  };

  return (
    <nav style={{ padding: 10, background: "#eee", marginBottom: 20 }}>
      <Link to="/" style={{ marginRight: 10 }}>Home</Link>
      <Link to="/trips" style={{ marginRight: 10 }}>Trips</Link>
      <Link to="/bookings" style={{ marginRight: 10 }}>My Bookings</Link>
      {!token ? (
        <>
          <Link to="/login" style={{ marginRight: 10 }}>Login</Link>
          <Link to="/register">Register</Link>
        </>
      ) : (
        <button onClick={handleLogout}>Logout</button>
      )}
    </nav>
  );
}
