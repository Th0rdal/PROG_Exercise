package at.ac.fhcampuswien.fhmdb.api;

public class MovieApiRequestBuilder {

    private StringBuilder url;
    boolean firstQuery = true;

    public MovieApiRequestBuilder(String base) {
        this.url = new StringBuilder(base);
    }

    private void appendDelimiter() {
        if (this.firstQuery) {
            this.url.append("?");
            firstQuery = false;
        }else {
            this.url.append("&");
        }
    }

    public MovieApiRequestBuilder query(String query) {
        if (query.equals("")) {
            return this;
        }
        this.appendDelimiter();
        this.url.append("query=").append(query);
        return this;
    }

    public MovieApiRequestBuilder genre(String genre) {
        if (genre.equals("NONE")) {
            return this;
        }
        this.appendDelimiter();
        this.url.append("genre=").append(genre);
        return this;
    }

    public MovieApiRequestBuilder releaseYear(String year) {
        if (year.equals("No release year filter")) {
            return this;
        }
        this.appendDelimiter();
        this.url.append("releaseYear=").append(year);
        return this;
    }

    public MovieApiRequestBuilder rating(String rating) {
        if (rating.equals("No rating filter")) {
            return this;
        }
        this.appendDelimiter();
        this.url.append("ratingFrom=").append(rating);
        return this;
    }

    public String build() {
        return this.url.toString();
    }
}
