package com.etec.tourtripapi.payment.service.Implemtation;

import com.etec.tourtripapi.common.exception.NotFoundException;
import com.etec.tourtripapi.common.utils.CodeGenerator;
import com.etec.tourtripapi.payment.dto.request.InvoiceRequest;
import com.etec.tourtripapi.payment.dto.response.InvoiceResponse;
import com.etec.tourtripapi.payment.entity.Booking;
import com.etec.tourtripapi.payment.entity.Invoice;
import com.etec.tourtripapi.payment.mapper.InvoiceMapper;
import com.etec.tourtripapi.payment.repository.BookingRepository;
import com.etec.tourtripapi.payment.repository.InvoiceRepository;
import com.etec.tourtripapi.payment.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImp implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final BookingRepository bookingRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new NotFoundException("Booking not found with id: " + request.getBookingId()));

        Invoice invoice = invoiceMapper.toEntity(request);
        invoice.setBooking(booking);
        
        if (invoice.getInvoiceNo() == null) {
            invoice.setInvoiceNo(CodeGenerator.generate("INV"));
        }
        
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(savedInvoice);
    }

    @Override
    @Transactional
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest request) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + id));

        invoiceMapper.updateEntityFromRequest(request, invoice);

        Invoice savedInvoice = invoiceRepository.save(invoice);
        return invoiceMapper.toResponse(savedInvoice);
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + id));
        return invoiceMapper.toResponse(invoice);
    }

    @Override
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new NotFoundException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }
}
