package com.hub.brokeragefirm.controller;

import com.hub.brokeragefirm.dto.request.DepositRequest;
import com.hub.brokeragefirm.dto.request.WithdrawRequest;
import com.hub.brokeragefirm.dto.response.AssetResponse;
import com.hub.brokeragefirm.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
@CrossOrigin
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<List<AssetResponse>> listAssets(@RequestParam Long customerId) {
        return ResponseEntity.ok(assetService.listAssets(customerId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody DepositRequest request) {
        assetService.deposit(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody WithdrawRequest request) {
        assetService.withdraw(request);
        return ResponseEntity.ok().build();
    }
}
