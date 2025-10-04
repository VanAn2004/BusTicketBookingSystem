import api from "./axios";

// Đăng nhập -> lấy token
export const login = (data) => api.post("/auth/token", data);

// Đăng xuất
export const logout = (data) => api.post("/auth/logout", data);

// Đăng ký user mới
export const register = (data) => api.post("/users/registration", data);

// Làm mới token
export const refresh = (data) => api.post("/auth/refresh", data);

// Kiểm tra token hợp lệ (introspect)
export const introspect = (data) => api.post("/auth/introspect", data);

// Quên mật khẩu
export const forgotPassword = (data) => api.post("/auth/forgot-password", data);

// Đặt lại mật khẩu (FE gửi raw string cho backend)
export const resetPassword = (token, newPassword) =>
  api.post(`/auth/reset-password/${token}`, newPassword, {
    headers: { "Content-Type": "text/plain" },
  });

