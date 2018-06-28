package com.item.eshop.mapper;

import com.item.eshop.model.Status;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportMapper {

    int countUser();
    List<Status> countUserByStatus();
    int countOrder();
    List<Status> countOrderByStatus();
    int countGood();
    List<Status> countGoodByStatus();
    double countAmountSum();
}
