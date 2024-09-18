package com.example.BackEndSem4.services.history;

import com.example.BackEndSem4.dtos.HistoryDTO;
import com.example.BackEndSem4.exceptions.DataNotFoundException;
import com.example.BackEndSem4.models.History;




public interface IHistoryService {


    History updateHistory(Long id, HistoryDTO historyDTO);

    History getHistoryByBookingId(Long bookingId) throws DataNotFoundException;

}
