package org.tn.esprit.commons.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ConsulServiceData {

    private String name;
    private String id;
    private String address;
    private int port;

}
