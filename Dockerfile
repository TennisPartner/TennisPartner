FROM redis:latest 
COPY ["redis.conf","/data/redis.conf"] 
RUN addgroup --system --gid 1000 worker
RUN adduser --system --uid 1000 --ingroup worker --disabled-password worker
USER worker:worker

EXPOSE 6379 
ENTRYPOINT ["redis-server","/data/redis.conf"]
