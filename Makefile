doc:
	sbt doc
compile:
	sbt clean
	sbt compile
build:
	sbt assembly
report:
	sbt clean coverage test
	sbt coverageReport
