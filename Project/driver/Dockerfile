FROM python
RUN pip install docker
WORKDIR /app
COPY . /app
EXPOSE 5000
CMD ["python", "driver.py"]