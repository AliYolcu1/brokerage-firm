package com.hub.brokeragefirm.controller;

import com.hub.brokeragefirm.dto.request.MoneyTransferRequest;
import com.hub.brokeragefirm.dto.response.AssetResponse;
import com.hub.brokeragefirm.dto.response.MoneyTransferResponse;
import com.hub.brokeragefirm.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping("/list")
    public ResponseEntity<List<AssetResponse>> listAssets(@RequestParam Long customerId) {
        return ResponseEntity.ok(assetService.listAssets(customerId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<MoneyTransferResponse> deposit(@RequestBody MoneyTransferRequest request) {
        return ResponseEntity.ok(assetService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<MoneyTransferResponse> withdraw(@RequestBody MoneyTransferRequest request) {
        return ResponseEntity.ok(assetService.withdraw(request));
    }
}
