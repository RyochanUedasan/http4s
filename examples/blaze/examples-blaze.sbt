name := "http4s-examples-blaze"

description := "Runs the examples in http4s' blaze runner"

publishArtifact := false

fork := true

libraryDependencies += metricsJson

seq(Revolver.settings: _*)




