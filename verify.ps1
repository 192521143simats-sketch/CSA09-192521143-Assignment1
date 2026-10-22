$ErrorActionPreference = 'Stop'
& (Join-Path $PSScriptRoot 'run-all.ps1')
foreach ($pair in @(@('Task03','ParkingFacility'), @('Task08','FeeDemo'))) {
    $task = $pair[0]
    $main = $pair[1]
    $actual = & java -cp "$(Join-Path $PSScriptRoot "build/$task");$(Join-Path $PSScriptRoot 'build')" $main
    if ($LASTEXITCODE -ne 0) { throw 'Exercise execution failed' }
    $expected = Get-Content (Join-Path $PSScriptRoot "$task/expected.txt")
    if (Compare-Object $expected $actual) { throw "$task output mismatch" }
}
Write-Output 'Verified: 74 assertions and both supplied expected outputs.'
