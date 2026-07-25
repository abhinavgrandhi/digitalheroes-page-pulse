# Page Pulse Backend

## Overview

Page Pulse Backend is a REST API built using Spring Boot. It accepts a website URL, analyzes the webpage, and returns useful information in JSON format.

## Features

- Validate website URL
- Get HTTP status code
- Measure response time
- Extract page title
- Extract meta description
- Count H1 tags
- Count images without alt text
- Calculate approximate word count
- Handle invalid URLs and non-HTML pages

## Tech Stack

- Java
- Spring Boot
- Jsoup
- Maven
- JUnit 5

## Project Structure

```
src
├── controller
├── dto
├── service
├── exception
└── resources
```

## API Endpoint

**POST**

```
/api/audit
```

### Request

```json
{
  "url": "https://example.com"
}
```

### Response

```json
{
  "status": 200,
  "responseTime": 120,
  "title": "Example Domain",
  "metaDescription": "",
  "h1Count": 1,
  "imagesWithoutAlt": 0,
  "wordCount": 19
}
```

## Run the Project

1. Clone the repository.
2. Open the project in your IDE.
3. Run the application.

Or use Maven:

```bash
mvn spring-boot:run
```

The server runs at:

```
http://localhost:8080
```

## Testing

JUnit tests are included for:

- Valid URL
- Invalid URL
- Non-HTML response

## Live Demo

Backend:

```
https://digitalheroes-page-pulse-production.up.railway.app/api/audit
```

Frontend:

```
https://sunny-lamington-795431.netlify.app/
```

## Author

**Abhinav Grandhi**
