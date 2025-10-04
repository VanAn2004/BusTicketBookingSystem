function BusCard({ bus }) {
  return (
    <div style={{ border: "1px solid #ddd", padding: 10, marginBottom: 10 }}>
      <h3>{bus?.name || "Bus Name"}</h3>
      <p>Seats: {bus?.seats || 0}</p>
    </div>
  );
}
export default BusCard;
