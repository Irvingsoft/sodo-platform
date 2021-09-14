package cool.sodo.common.core.support;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import lombok.val;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;

import static feign.form.ContentType.MULTIPART;

/**
 * Feign 调用 编码器
 *
 * @author TimeChaser
 * @date 2020/12/2 3:15 下午
 */
public class FeignSpringFormEncoder extends FormEncoder {
    /**
     * 使用默认Feign编码器作为委托的构造函数。
     */
    public FeignSpringFormEncoder() {
        this(new Default());
    }

    /**
     * 具有指定委托编码器的构造函数。
     *
     * @param delegate 委托编码器（如果此编码器无法编码对象）。
     */
    public FeignSpringFormEncoder(Encoder delegate) {
        super(delegate);

        val processor = (MultipartFormContentProcessor) getContentProcessor(MULTIPART);
        processor.addFirstWriter(new SpringSingleMultipartFileWriter());
        processor.addFirstWriter(new SpringManyMultipartFilesWriter());
    }

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {

        if (bodyType.equals(MultipartFile.class)) {
            MultipartFile file = (MultipartFile) object;
            Map<String, Object> data = Collections.singletonMap(file.getName(), object);
            super.encode(data, MAP_STRING_WILDCARD, template);
            return;
        } else if (bodyType.equals(MultipartFile[].class)) {
            MultipartFile[] file = (MultipartFile[]) object;
            if (file != null) {
                Map<String, Object> data = Collections.singletonMap(file.length == 0 ? "" : file[0].getName(), object);
                super.encode(data, MAP_STRING_WILDCARD, template);
                return;
            }
        }
        super.encode(object, bodyType, template);
    }

    private boolean isMultipartFileCollection(Object object) {

        if (!(object instanceof Iterable)) {
            return false;
        }
        val iterable = (Iterable<?>) object;
        val iterator = iterable.iterator();
        return iterator.hasNext() && iterator.next() instanceof MultipartFile;
    }
}