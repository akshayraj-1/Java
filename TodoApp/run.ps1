$projectRoot = $PSScriptRoot
$srcDir = Join-Path $projectRoot "src"

$outDir = Join-Path $projectRoot "out"

if (-not (Test-Path $outDir)) {
    New-Item -ItemType Directory -Path $outDir | Out-Null
}

Write-Host "Compiling Java files..."
$javaFiles = Get-ChildItem $srcDir -Filter *.java -Recurse
if ($javaFiles) {
    javac -d $outDir $javaFiles.FullName
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Compilation successful."
        $todosFile = Join-Path $srcDir "todos.txt"
        if (Test-Path $todosFile) {
            Copy-Item $todosFile $outDir
        }
        Write-Host "Running the application..."
        java -cp $outDir Main
    } else {
        Write-Host "Compilation failed. Please check your Java files for errors."
    }
} else {
    Write-Host "No Java files found in the src directory."
}