# Use the official PostgreSQL image from Docker Hub
FROM postgres:14

# Set environment variables for the database
ENV POSTGRES_DB=task_management_db
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

# Expose the default PostgreSQL port
EXPOSE 5432

# Add any initialization scripts or SQL files if needed
# COPY init.sql /docker-entrypoint-initdb.d/

# Start PostgreSQL
CMD ["postgres"]