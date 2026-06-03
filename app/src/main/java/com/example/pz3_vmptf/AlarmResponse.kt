package com.example.pz3_vmptf

data class AlarmResponse(
    val version: Int,
    val info: String,
    val states: Map<String, RegionStatus>
)

data class RegionStatus(
    val enabled: Boolean,
    val districts: Map<String, DistrictStatus> = emptyMap(),
    val enabled_at: String?,
    val disabled_at: String?
)

data class DistrictStatus(
    val enabled: Boolean,
    val type: String?,
    val enabled_at: String?,
    val disabled_at: String?
)
