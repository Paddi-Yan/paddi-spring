package org.springframework.core.convert.converter;

import java.util.Objects;
import java.util.Set;

/**
 * @Author: Paddi-Yan
 * @Project: mini-spring
 * @CreatedTime: 2023年03月20日 21:41:26
 */
public interface GenericConverter {
    Set<ConvertiblePair> getConvertibleTypes();

    Object convert(Object source, Class sourceType, Class targetType);

    final class ConvertiblePair {
        private final Class<?> sourceType;

        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        @Override
        public boolean equals(Object o) {
            if(this == o) {
                return true;
            }
            if(o == null || getClass() != o.getClass()) {
                return false;
            }

            ConvertiblePair that = (ConvertiblePair) o;

            if(!Objects.equals(sourceType, that.sourceType)) {
                return false;
            }
            return Objects.equals(targetType, that.targetType);
        }

        @Override
        public int hashCode() {
            int result = sourceType != null ? sourceType.hashCode() : 0;
            result = 31 * result + (targetType != null ? targetType.hashCode() : 0);
            return result;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public Class<?> getTargetType() {
            return targetType;
        }
    }
}
