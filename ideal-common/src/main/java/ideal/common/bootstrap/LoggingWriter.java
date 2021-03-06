/*
 * Copyright (C) 2018 The Sylph Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ideal.common.bootstrap;

import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

class LoggingWriter
        extends StringWriter
{
    private final Logger logger;

    public LoggingWriter(Logger logger)
    {
        this.logger = logger;
    }

    @Override
    public void close()
            throws IOException
    {
        flush();
        super.close();
    }

    @Override
    public void flush()
    {
        BufferedReader in = new BufferedReader(new StringReader(getBuffer().toString()));
        while (true) {
            try {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                logger.info(line);
            }
            catch (IOException e) {
                throw new Error(e); // should never get here
            }
        }

        getBuffer().setLength(0);
    }
}
