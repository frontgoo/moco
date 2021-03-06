package com.github.dreamhead.moco.junit;

import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.RestServer;
import com.github.dreamhead.moco.Runner;
import com.github.dreamhead.moco.SocketServer;
import com.github.dreamhead.moco.resource.Resource;
import org.junit.rules.ExternalResource;

import static com.github.dreamhead.moco.Moco.file;
import static com.github.dreamhead.moco.MocoJsonRunner.jsonHttpServer;
import static com.github.dreamhead.moco.MocoJsonRunner.jsonSocketServer;
import static com.github.dreamhead.moco.Runner.runner;
import static com.github.dreamhead.moco.util.Preconditions.checkNotNullOrEmpty;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class MocoJunitRunner extends ExternalResource {
    private final Runner runner;

    private MocoJunitRunner(final Runner runner) {
        this.runner = runner;
    }

    public static MocoJunitRunner jsonHttpRunner(final int port, final String filename) {
        checkArgument(port > 0, "Port must be greater than zero");
        checkNotNullOrEmpty(filename, "Filename should not be null");
        return jsonHttpRunner(port, file(filename));
    }

    public static MocoJunitRunner jsonHttpRunner(final int port, final Resource file) {
        checkArgument(port > 0, "Port must be greater than zero");
        checkNotNull(file, "Filename should not be null");
        return new MocoJunitRunner(runner(jsonHttpServer(port, file)));
    }

    public static MocoJunitRunner httpRunner(final HttpServer server) {
        checkNotNull(server, "Server should not be null");
        return new MocoJunitRunner(runner(server));
    }

    public static MocoJunitRunner restRunner(final RestServer server) {
        checkNotNull(server, "Server should not be null");
        return httpRunner(server);
    }

    public static MocoJunitRunner jsonSocketRunner(final int port, final String filename) {
        checkArgument(port > 0, "Port must be greater than zero");
        checkNotNullOrEmpty(filename, "Filename should not be null");
        return jsonSocketRunner(port, file(filename));
    }

    public static MocoJunitRunner jsonSocketRunner(final int port, final Resource filename) {
        checkArgument(port > 0, "Port must be greater than zero");
        checkNotNull(filename, "Filename should not be null");
        return new MocoJunitRunner(runner(jsonSocketServer(port, filename)));
    }

    public static MocoJunitRunner socketRunner(final SocketServer server) {
        checkNotNull(server, "Server should not be null");
        return new MocoJunitRunner(runner(server));
    }

    public static MocoJunitRunner jsonRestRunner(int port, String filename) {
        checkArgument(port > 0, "Port must be greater than zero");
        checkNotNullOrEmpty(filename, "Filename should not be null");
        return jsonRestRunner(port, file(filename));
    }

    public static MocoJunitRunner jsonRestRunner(int port, Resource filename) {
        checkArgument(port > 0, "Port must be greater than zero");
        checkNotNull(filename, "Filename should not be null");
        return jsonHttpRunner(port, filename);
    }

    @Override
    protected void before() throws Throwable {
        runner.start();
    }

    @Override
    protected void after() {
        if (runner != null) {
            runner.stop();
        }
    }
}
