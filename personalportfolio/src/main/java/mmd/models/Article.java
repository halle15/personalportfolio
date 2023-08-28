package mmd.models;

import java.sql.Date;

import java.util.List;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NonNull
	private String title;
	@NonNull
	private String author;
	@Column(length = 1000)
	@NonNull
	private String desc; // shorthand description, for tooltips etc.
	@Column(length = 5000)
	@NonNull
	private String text; // content of the article

	private String mainImg; // used for thumbnail
	private List<String> images;

	private String mainTag; // the big blue tag lol
	private List<String> tags;

	private Date pubDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getMainTag() {
		return mainTag;
	}

	public void setMainTag(String mainTag) {
		this.mainTag = mainTag;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", author=" + author + ", text=" + text + ", mainImg="
				+ mainImg + ", images=" + images + ", mainTag=" + mainTag + ", tags=" + tags + "]";
	}

	public Article(String title, String author, String desc, String text, String mainImg, List<String> images,
			String mainTag, List<String> tags, Date date) {
		super();
		this.title = title;
		this.author = author;
		this.desc = desc;
		this.text = text;
		this.mainImg = mainImg;
		this.images = images;
		this.mainTag = mainTag;
		this.tags = tags;
		this.pubDate = date;
	}

	protected Article() {
		super();
	}

}
