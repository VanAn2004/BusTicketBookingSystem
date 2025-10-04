function TripCard({ trip }) {
  return (
    <div style={{ border: "1px solid #ddd", padding: 10, marginBottom: 10 }}>
      <h3>{trip?.name || "Trip Name"}</h3>
      <p>From: {trip?.from || "???"} → To: {trip?.to || "???"}</p>
      <p>Price: {trip?.price || 0} VND</p>
    </div>
  );
}
export default TripCard;
