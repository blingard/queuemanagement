package com.example.messagequeuemanagement.records;

import lombok.Builder;

@Builder
public record MotifRecord(Long id, String name, String initiale, Long userId) {
}
