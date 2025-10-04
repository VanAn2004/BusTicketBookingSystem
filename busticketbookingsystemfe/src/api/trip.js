import api from "./axios";

export const filterTrips = (params) => api.get("/trips/filter", { params });
export const getTripById = (id) => api.get(`/trips/${id}`);
export const createTrip = (data) => api.post("/trips", data);
export const updateTrip = (id, data) => api.put(`/trips/${id}`, data);
export const deleteTrip = (id) => api.delete(`/trips/${id}`);
