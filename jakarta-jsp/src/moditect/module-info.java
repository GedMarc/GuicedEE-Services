open module jakarta.servlet.jsp {

	exports jakarta.servlet.jsp;

	requires transitive java.logging;
	requires java.xml;

	requires transitive jakarta.el;
	requires transitive java.servlet;

	requires java.desktop;
	requires java.compiler;
}
