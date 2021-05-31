package org.tn.esprit.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.tn.esprit.commons.domain.AbstractEntity;
import org.tn.esprit.entities.enumeration.Type;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.io.File;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "components")
@NoArgsConstructor
public class Component extends AbstractEntity {


  @Column(name = "name")
  private String name;

  @Column(name = "source_id")
  private String sourceId;

  @Column(name = "destination_id")
  private String destinationId;


  @Column(name = "link")
  private String link;


  @Column(name = "file")
  private String file;

  @Column(name = "file_type")
  private String fileType ;

  @Enumerated(EnumType.STRING)
  private Type type;

  private int step ;

  @Column(name = "text_color")
  private String textColor;
  @Column(name = "background_color")
  private String backgroundColor;
  private Boolean bold;
  private Boolean italic;
  private Boolean underline;
  @Column(name = "font_size")
  private int fontSize;
  @Column(name = "font_family")
  private String fontFamily;

  @Column(name = "quiz_score")
  private Integer quizScore ;

  @Column(name = "time_per_question")
  private Float timePerQuestion;


  @ManyToOne(fetch = FetchType.LAZY)
  private Workflow workflow;

  @OneToMany(mappedBy = "component")
  @JsonIgnore
  private List<Quiz> quizzes ;


  @OneToMany(mappedBy = "component")
  @JsonIgnore
  private List<Field> fields ;

  public Component(String name, String sourceId, String destinationId, String link, String file, String fileType, Type type,
                   int step, String textColor, String backgroundColor, Boolean bold, Boolean italic, Boolean underline,
                   int fontSize, String fontFamily, Workflow workflow,
                   Integer quizScore, Float timePerQuestion) {
    this.name = name;
    this.sourceId = sourceId;
    this.destinationId = destinationId;
    this.link = link;
    this.file = file;
    this.fileType = fileType;
    this.type = type;
    this.step = step;
    this.textColor = textColor;
    this.backgroundColor = backgroundColor;
    this.bold = bold;
    this.italic = italic;
    this.underline = underline;
    this.fontSize = fontSize;
    this.fontFamily = fontFamily;
    this.workflow = workflow;
    this.quizScore = quizScore ;
    this.timePerQuestion = timePerQuestion ;
  }
}
