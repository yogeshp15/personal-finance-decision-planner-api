
package com.finance.finance_planner.controller;

import com.finance.finance_planner.dto.DashboardResponseDTO;
import com.finance.finance_planner.dto.MonthlySummaryResponseDTO;
import com.finance.finance_planner.service.DashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // Get Overall Dashboard Data
    @GetMapping
    public ResponseEntity<DashboardResponseDTO> getDashboardData() {

        DashboardResponseDTO response =
                dashboardService.getDashboardData();

        return ResponseEntity.ok(response);
    }

    // Get Monthly Financial Summary
    @GetMapping("/monthly")
    public ResponseEntity<MonthlySummaryResponseDTO> getMonthlySummary(
            @RequestParam YearMonth month) {

        MonthlySummaryResponseDTO response =
                dashboardService.getMonthlySummary(month);

        return ResponseEntity.ok(response);
    }
}
