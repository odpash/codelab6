
package server.execution;

import common.net.CommandResult;
import common.net.Request;

import java.io.IOException;

public interface Executable {
    CommandResult execute(Request<?> request) throws IOException;
}