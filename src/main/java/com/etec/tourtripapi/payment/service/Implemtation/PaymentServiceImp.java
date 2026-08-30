package com.etec.tourtripapi.payment.service.Implemtation;

import com.etec.tourtripapi.common.enums.PaymentStatus;
import com.etec.tourtripapi.common.exception.NotFoundException;
import com.etec.tourtripapi.payment.dto.request.PaymentRequest;
import com.etec.tourtripapi.payment.dto.response.PaymentResponse;
import com.etec.tourtripapi.payment.entity.Payment;
import com.etec.tourtripapi.payment.mapper.PaymentMapper;
import com.etec.tourtripapi.payment.repository.PaymentRepository;
import com.etec.tourtripapi.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = paymentMapper.toEntity(request);
        payment.setPaymentStatus(PaymentStatus.pending);
        payment.setPaymentDate(LocalDateTime.now());
        
        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found with id: " + id));
        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream()
                .map(paymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new NotFoundException("Payment not found with id: " + id);
        }
        paymentRepository.deleteById(id);
    }
}
