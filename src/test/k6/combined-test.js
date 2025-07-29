import http from 'k6/http';
import {check} from 'k6';

export const options = {
    scenarios: {
        test_test_api: {
            executor: 'constant-vus',
            exec: 'test_test_api',
            vus: 1,
            duration: '10s',
        }
        ,

        test_test_api2: {
            executor: 'constant-vus',
            exec: 'test_test_api2',
            vus: 2,
            duration: '10s',
        }

    },
    thresholds: {
        http_req_failed: ['rate < 0.01'], // http errors should be less than 1%
        http_req_duration: ['p(95) < 500'], // 95% of requests should be below 500ms
    },
};


export function test_test_api() {
    const url = 'http://localhost:8080/test/hjk';
    const params = {headers: {}};
    const payload = null;

    const res = http.get(url, params);

    check(res, {
        'status is 200 on test-api': (r) => r.status === 200,
    });
}


export function test_test_api2() {
    const url = 'http://localhost:8080/test2/hjk';
    const params = {headers: {}};
    const payload = null;

    const res = http.get(url, params);

    check(res, {
        'status is 200 on test-api2': (r) => r.status === 200,
    });
}

        