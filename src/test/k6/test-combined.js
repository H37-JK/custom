import http from 'k6/http';
import { sleep, group, check } from 'k6';

export const options = {
  vus: 10,
  duration: '2s',
};

export default function () {
  group('API Test 1 - /test endpoint', function () {
    const res = http.get('http://localhost:8080/test/hjk');
    check(res, {
      '[API Test 1] status is 200': (r) => r.status === 200,
    });
    sleep(1);
  });

  group('API Test 2 - /test2 endpoint', function () {
    const res = http.get('http://localhost:8080/test2/hjk');
    check(res, {
      '[API Test 2] status is 200': (r) => r.status === 200,
    });
    // console.log(`[API Test 2] Request to /test2 | Status: ${res.status}`);
    sleep(1);
  });
}