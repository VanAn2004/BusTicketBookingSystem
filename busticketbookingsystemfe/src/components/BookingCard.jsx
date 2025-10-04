function BookingCard({ booking }) {
  return (
    <div style={{ border: "1px solid #ddd", padding: 10, marginBottom: 10 }}>
      <h3>Booking ID: {booking?.id || "N/A"}</h3>
      <p>Trip: {booking?.trip || "Unknown"}</p>
      <p>Seats: {booking?.seatCount || 0}</p>
    </div>
  );
}
export default BookingCard;
