package com.etec.tourtripapi.payment.service.Implemtation;

import com.etec.tourtripapi.common.exception.NotFoundException;
import com.etec.tourtripapi.payment.dto.request.InvoiceRequest;
import com.etec.tourtripapi.payment.dto.response.InvoiceResponse;
import com.etec.tourtripapi.payment.entity.Invoice;
import com.etec.tourtripapi.payment.mapper.InvoiceMapper;
import com.etec.tourtripapi.payment.repository.InvoiceRepository;
import com.etec.tourtripapi.payment.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImp implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    @Override
    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoice invoice = invoiceMapper.toEntity(request);
        
        // Generate unique invoice number
        if (invoice.getInvoiceNo() == null) {
            invoice.setInvoiceNo("INV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
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
