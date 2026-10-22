$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
New-Item -ItemType Directory -Path build -Force | Out-Null
$sources = Get-ChildItem src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
& javac --release 17 -d build $sources
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed' }
