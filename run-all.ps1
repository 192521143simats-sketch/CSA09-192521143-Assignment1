$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
1..12 | ForEach-Object {
    $task = 'Task{0:D2}' -f $_
    Write-Output $task
    & (Join-Path $PSScriptRoot "$task/run.ps1")
}
