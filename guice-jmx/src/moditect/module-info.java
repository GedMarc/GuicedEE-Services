module com.google.guice.extensions.jmx {
	exports com.google.inject.tools.jmx;

	requires com.google.guice;
	requires java.management;

	opens com.google.inject.tools.jmx to com.google.guice;
}
