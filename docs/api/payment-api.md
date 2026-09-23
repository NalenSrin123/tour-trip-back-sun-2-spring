# Payment Module API Reference

Base URL: `http://localhost:8080/api/v1`

Every endpoint returns the common envelope:

```json
{
  "message": "string",
  "status": 200,
  "data": { }
}
```

Errors are handled globally (`common/exception/GlobalExceptionHandler`) and returned in the same envelope with `data: null`:

| Exception | HTTP status |
|---|---|
| `NotFoundException` | 404 |
| `BadRequestException` | 400 |
| `ValidationException` | 400 |
| `UnauthorizedException` | 401 |
| any other exception | 500 |

---

## Bookings — `/bookings`

| Method | Path | Description |
|---|---|---|
| POST | `/bookings` | Create a booking with its participants |
| GET | `/bookings` | List all bookings |
| GET | `/bookings/{id}` | Get one booking |
| PUT | `/bookings/{id}` | Update `userId`, `tourScheduleId`, `specialRequests`, `bookingType` (does **not** touch participants, status, price, or member count) |
| DELETE | `/bookings/{id}` | Soft-delete a booking |

**BookingRequest**
```json
{
  "userId": 1,
  "tourScheduleId": 101,
  "specialRequests": "Vegetarian meals",
  "bookingType": "Family",
  "participantRequests": [
    { "name": "Sok Dara", "ageGroup": "Adult", "sex": "Male" }
  ]
}
```

**BookingResponse**
```json
{
  "id": 1,
  "userId": 1,
  "tourScheduleId": 101,
  "totalPrice": 450.00,
  "specialRequests": "Vegetarian meals",
  "bookingStatus": "Pending",
  "bookingType": "Family",
  "memberCount": 3,
  "participants": [
    { "id": 1, "name": "Sok Dara", "ageGroup": "Adult", "sex": "Male" }
  ]
}
```

New bookings are always created with `bookingStatus = Pending`.

---

## Participants — `/participants`

| Method | Path | Description |
|---|---|---|
| POST | `/participants?bookingId={bookingId}` | Add a participant to an existing booking |
| GET | `/participants` | List all participants |
| GET | `/participants/{id}` | Get one participant |
| PUT | `/participants/{id}` | Update `name`, `ageGroup`, `sex` |
| DELETE | `/participants/{id}` | Soft-delete a participant |

**ParticipantRequest**
```json
{ "name": "Chan Thida", "ageGroup": "Adult", "sex": "Female" }
```

`sex` accepts `Male`, `Female`, `Other`.

---

## Invoices — `/invoices`

| Method | Path | Description |
|---|---|---|
| POST | `/invoices` | Create an invoice for a booking |
| GET | `/invoices` | List all invoices |
| GET | `/invoices/{id}` | Get one invoice (includes its payments) |
| PUT | `/invoices/{id}` | Update `subTotal`, `taxAmount`, `totalAmount` (booking link is immutable after creation) |
| DELETE | `/invoices/{id}` | Soft-delete an invoice |

**InvoiceRequest**
```json
{
  "bookingId": 1,
  "subTotal": 400.00,
  "taxAmount": 50.00,
  "totalAmount": 450.00
}
```

`invoiceNo` is not part of the request DTO — it is always generated server-side (`INV-XXXXXXXX`).

---

## Payments — `/payments`

| Method | Path | Description |
|---|---|---|
| POST | `/payments` | Charge an invoice through the configured payment gateway |
| GET | `/payments` | List all payments |
| GET | `/payments/{id}` | Get one payment (includes its receipt, if any) |
| PUT | `/payments/{id}` | Update `amount`, `paymentMethod`, or attach a `receiptId` (does **not** re-run the gateway or change status/transaction id) |
| DELETE | `/payments/{id}` | Soft-delete a payment |

**PaymentRequest**
```json
{
  "invoiceId": 1,
  "receiptId": 1,
  "amount": 450.00,
  "paymentMethod": "aba_pay"
}
```

`invoiceId` must reference an existing invoice (404 otherwise). `receiptId` is optional — link an existing receipt if you have one; omit it (or send `null`) to leave the payment without a receipt. `paymentMethod` accepts `card`, `bank_transfer`, `aba_pay` — each is routed by `PaymentGatewayFactory` (`payment/gateway`) to its own mock gateway, which returns a `transactionId` (e.g. `ABA-A1B2C3D4`) and a success/failure result. On create, `paymentStatus` becomes `paid` or `failed` accordingly and `paymentDate` is set to now.

**PaymentResponse**
```json
{
  "id": 1,
  "invoiceId": 1,
  "amount": 450.00,
  "paymentMethod": "aba_pay",
  "paymentStatus": "paid",
  "transactionId": "ABA-A1B2C3D4",
  "paymentDate": "2026-09-13T10:30:00",
  "receipt": null
}
```

---

## Receipts — `/receipts`

| Method | Path | Description |
|---|---|---|
| POST | `/receipts` | Create a receipt |
| GET | `/receipts` | List all receipts |
| GET | `/receipts/{id}` | Get one receipt |
| PUT | `/receipts/{id}` | Update any field below (`receiptNo` stays immutable after creation) |
| DELETE | `/receipts/{id}` | Soft-delete a receipt |

**ReceiptRequest**
```json
{
  "tourTittle": "Angkor Wat Tour",
  "tourDate": "2026-10-01T07:30:00",
  "numTravelers": 3,
  "subTotal": 400.00,
  "taxAmount": 50.00,
  "totalPaid": 450.00,
  "paymentMethod": "aba_pay",
  "pdfUrl": "/uploads/receipts/REC-2026-0010.pdf"
}
```

`receiptNo` is not part of the request DTO — it is always generated server-side (`REC-XXXXXXXX`). `issuedAt` is likewise not settable; it's stamped automatically at creation time.

---

## Related docs

- [ERD](../erd/erd.md)
- [OpenAPI spec](../swagger/openapi.yaml)
- [Postman collection](../postman/TourTripAPI.postman_collection.json)
