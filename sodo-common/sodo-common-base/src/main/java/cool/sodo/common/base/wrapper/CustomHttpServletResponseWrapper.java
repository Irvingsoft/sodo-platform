package cool.sodo.common.base.wrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author TimeChaser
 * @date 2021/7/16 10:56
 */
@Deprecated
public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {

    private final CustomResponsePrintWriter writer;
    private final ByteArrayOutputStream output;

    public CustomHttpServletResponseWrapper(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
        output = new ByteArrayOutputStream();
        writer = new CustomResponsePrintWriter(output);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new CustomServletOutputStream(output);
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
        output.close();
        writer.close();
    }

    public String getContent() {
        return new String(this.getBytes(), StandardCharsets.UTF_8);
    }

    public byte[] getBytes() {
        byte[] bytes = output.toByteArray();
        if (null != writer) {
            writer.close();
            return bytes;
        }

        if (null != output) {
            try {
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    static class CustomServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream outputStream;

        public CustomServletOutputStream(ByteArrayOutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) throws IOException {
            // 将数据写到 stream　中
            outputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

    private static class CustomResponsePrintWriter extends PrintWriter {
        ByteArrayOutputStream output;

        public CustomResponsePrintWriter(ByteArrayOutputStream output) {
            super(output);
            this.output = output;
        }

        public ByteArrayOutputStream getByteArrayOutputStream() {
            return output;
        }
    }
}