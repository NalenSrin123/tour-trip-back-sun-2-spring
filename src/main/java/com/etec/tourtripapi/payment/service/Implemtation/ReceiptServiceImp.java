package com.etec.tourtripapi.payment.service.Implemtation;

import com.etec.tourtripapi.common.exception.NotFoundException;
import com.etec.tourtripapi.common.utils.CodeGenerator;
import com.etec.tourtripapi.payment.dto.request.ReceiptRequest;
import com.etec.tourtripapi.payment.dto.response.ReceiptResponse;
import com.etec.tourtripapi.payment.entity.Invoice;
import com.etec.tourtripapi.payment.entity.Receipt;
import com.etec.tourtripapi.payment.mapper.ReceiptMapper;
import com.etec.tourtripapi.payment.repository.InvoiceRepository;
import com.etec.tourtripapi.payment.repository.ReceiptRepository;
import com.etec.tourtripapi.payment.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceiptServiceImp implements ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final InvoiceRepository invoiceRepository;
    private final ReceiptMapper receiptMapper;

    @Override
    @Transactional
    public ReceiptResponse createReceipt(ReceiptRequest request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + request.getInvoiceId()));

        Receipt receipt = receiptMapper.toEntity(request);
        receipt.setInvoice(invoice);
        receipt.setReceiptNo(CodeGenerator.generate("REC"));

        Receipt savedReceipt = receiptRepository.save(receipt);
        return receiptMapper.toResponse(savedReceipt);
    }

    @Override
    @Transactional
    public ReceiptResponse updateReceipt(Long id, ReceiptRequest request) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receipt not found with id: " + id));

        if (request.getInvoiceId() != null) {
            Invoice invoice = invoiceRepository.findById(request.getInvoiceId())
                    .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + request.getInvoiceId()));
            receipt.setInvoice(invoice);
        }

        receiptMapper.updateEntityFromRequest(request, receipt);
        Receipt savedReceipt = receiptRepository.save(receipt);
        return receiptMapper.toResponse(savedReceipt);
    }

    @Override
    public ReceiptResponse getReceiptById(Long id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Receipt not found with id: " + id));
        return receiptMapper.toResponse(receipt);
    }

    @Override
    public List<ReceiptResponse> getAllReceipts() {
        return receiptRepository.findAll().stream()
                .map(receiptMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReceipt(Long id) {
        if (!receiptRepository.existsById(id)) {
            throw new NotFoundException("Receipt not found with id: " + id);
        }
        receiptRepository.deleteById(id);
    }
}