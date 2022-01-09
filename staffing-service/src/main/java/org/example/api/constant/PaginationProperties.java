package org.example.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "application.pagination")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public final class PaginationProperties {

    private final int minEntitiesPerPage;
    private final int maxEntitiesPerPage;
    private final int defaultPage;
    private final int defaultEntitiesPerPage;

}
