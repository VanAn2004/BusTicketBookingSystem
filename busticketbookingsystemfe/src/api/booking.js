import api from "./axios";

export const createBooking = (tripId, seatCount) =>
  api.post(`/api/bookings?tripId=${tripId}&seatCount=${seatCount}`);

export const myBookings = () => api.get("/api/bookings/me");

export const cancelBooking = (id) => api.delete(`/api/bookings/${id}`);
