import api from "./axios";

export const getCurrentUser = () => api.get("/users/me");
export const updateUser = (data) => api.put("/users/update", data);
export const getUserById = (id) => api.get(`/users/user/${id}`);
