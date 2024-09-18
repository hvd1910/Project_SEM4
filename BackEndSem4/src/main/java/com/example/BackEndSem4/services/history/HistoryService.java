package com.example.BackEndSem4.services.history;

import com.example.BackEndSem4.dtos.HistoryDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.History;
import com.example.BackEndSem4.models.Prescription;
import com.example.BackEndSem4.repositories.HistoryRepository;
import com.example.BackEndSem4.repositories.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class HistoryService implements IHistoryService{

    private  final HistoryRepository historyRepository;
    private  final PrescriptionRepository prescriptionRepository;

    @Transactional
    @Override
    public History updateHistory(Long id, HistoryDTO historyDTO) {
        // Tìm History theo ID
        History history = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("History not found by id: " + id));

        List<Prescription> prescriptions = prescriptionRepository.findAllByHistory(history);
//         Cập nhật các trường trong History
        history.setDiagnosis(historyDTO.getDiagnosis());
        history.setFilePrescription(historyDTO.getFilePrescription());
        History history1 = historyRepository.save(history);

        if (!prescriptions.isEmpty()) {
                prescriptionRepository.deleteAll(prescriptions);
        }

        // Xử lý các đơn thuốc
        if (historyDTO.getPrescriptions() != null) {
            // Xóa các Prescription hiện tại nếu có

            Set<Prescription> updatedPrescriptions = new HashSet<>();
            for (Prescription dtoPrescription : historyDTO.getPrescriptions()) {
                // Tạo đối tượng Prescription mới và lưu vào cơ sở dữ liệu
                Prescription newPrescription = Prescription.builder()
                        .history(history)
                        .medicine(dtoPrescription.getMedicine())
                        .desciptionUsage(dtoPrescription.getDesciptionUsage())
                        .unit(dtoPrescription.getUnit())
                        .build();
                updatedPrescriptions.add(newPrescription);
            }
            prescriptionRepository.saveAll(updatedPrescriptions);

        }
        return history1;
    }




    @Override
    public History getHistoryByBookingId(Long bookingId) throws DataNotFoundException {
        History history = historyRepository.findHistoryByBooking_Id(bookingId);
        if (history == null ) {
            throw  new DataNotFoundException("History not found by id" + bookingId);
        }
        return history;
    }

}
