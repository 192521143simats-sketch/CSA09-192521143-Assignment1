$ErrorActionPreference = 'Stop'
$root = Split-Path $PSScriptRoot -Parent
& (Join-Path $root 'compile.ps1')
New-Item -ItemType Directory -Path (Join-Path $root 'build/Task06') -Force | Out-Null
$sources = Get-ChildItem $PSScriptRoot -Filter *.java | ForEach-Object { $_.FullName }
& javac --release 17 -cp (Join-Path $root 'build') -d (Join-Path $root 'build/Task06') $sources
if ($LASTEXITCODE -ne 0) { throw 'Task compilation failed' }
& java -cp "$(Join-Path $root 'build/Task06');$(Join-Path $root 'build')" Task06Demo
if ($LASTEXITCODE -ne 0) { throw 'Execution failed' }
