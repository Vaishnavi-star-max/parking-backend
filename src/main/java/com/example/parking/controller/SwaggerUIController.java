package com.example.parking.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SwaggerUIController {

    @GetMapping("/swagger")
    @ResponseBody
    public String swaggerUI() {
        return """
                                <!DOCTYPE html>
                                <html lang="en">
                                <head>
                                    <meta charset="UTF-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <title>Parking Lot API - Swagger UI</title>
                                    <style>
                                        * { margin: 0; padding: 0; box-sizing: border-box; }
                                        body { font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; background: #fafafa; }
                                        .header { background: #89bf04; color: white; padding: 15px 20px; display: flex; align-items: center; }
                                        .header h1 { font-size: 24px; margin-left: 10px; }
                                        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
                                        .api-info { background: white; padding: 20px; border-radius: 8px; margin-bottom: 20px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                                        .api-info h2 { color: #3b4151; margin-bottom: 10px; }
                                        .api-info p { color: #3b4151; line-height: 1.5; }
                                        .auth-section { background: #fff3cd; border: 1px solid #ffeaa7; padding: 15px; border-radius: 5px; margin: 20px 0; }
                                        .auth-section h3 { color: #856404; margin-bottom: 10px; }
                                        .auth-button { background: #49cc90; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; margin-bottom: 20px; }
                                        .endpoint-group { background: white; margin-bottom: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
                                        .group-header { background: #f7f7f7; padding: 15px 20px; border-bottom: 1px solid #e8e8e8; cursor: pointer; display: flex; justify-content: space-between; align-items: center; }
                                        .group-header h3 { color: #3b4151; font-size: 18px; }
                                        .group-header .toggle { font-size: 14px; color: #666; }
                                        .endpoints { display: none; }
                                        .endpoints.show { display: block; }
                                        .endpoint { border-bottom: 1px solid #e8e8e8; }
                                        .endpoint:last-child { border-bottom: none; }
                                        .endpoint-header { padding: 15px 20px; cursor: pointer; display: flex; align-items: center; }
                                        .method { padding: 4px 8px; border-radius: 4px; color: white; font-weight: bold; font-size: 12px; margin-right: 15px; min-width: 60px; text-align: center; }
                                        .method.get { background: #61affe; }
                                        .method.post { background: #49cc90; }
                                        .method.put { background: #fca130; }
                                        .method.delete { background: #f93e3e; }
                                        .endpoint-path { color: #3b4151; font-weight: 500; }
                                        .endpoint-desc { color: #666; margin-left: auto; }
                                        .endpoint-details { display: none; padding: 20px; background: #f9f9f9; }
                                        .endpoint-details.show { display: block; }
                                        .try-button { background: #4990e2; color: white; border: none; padding: 8px 16px; border-radius: 4px; cursor: pointer; margin: 10px 0; }
                                        .example-box { background: #2d3748; color: #e2e8f0; padding: 15px; border-radius: 4px; margin: 10px 0; font-family: 'Courier New', monospace; font-size: 14px; overflow-x: auto; }
                                        .auth-input { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; margin: 5px 0; }
                                        .success { color: #28a745; }
                                        .error { color: #dc3545; }
                                        .result-container { margin-top: 15px; padding: 15px; background: #f8f9fa; border-radius: 4px; border: 1px solid #e9ecef; }
                                        .loading { color: #6c757d; font-style: italic; }
                                        .json-editor { width: 100%; min-height: 120px; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-family: 'Courier New', monospace; font-size: 14px; background: #f8f9fa; margin: 10px 0; resize: vertical; }
                                        .execute-button { background: #4990e2; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer; margin: 10px 0; font-weight: bold; }
                                        .execute-button:hover { background: #357abd; }
                                        .json-section { margin: 15px 0; }
                                        .json-label { font-weight: bold; margin-bottom: 5px; display: block; }
                                    </style>
                                </head>
                                <body>
                                    <div class="header">
                                        <span style="font-size: 20px;">🅿️</span>
                                        <h1>Parking Lot Management API</h1>
                                        <div style="margin-left: auto;">
                                            <span style="background: rgba(255,255,255,0.2); padding: 4px 8px; border-radius: 4px; font-size: 12px;">v1.0.0</span>
                                        </div>
                                    </div>

                                    <div class="container">
                                        <div class="api-info">
                                            <h2>Parking Lot Management API</h2>
                                            <p>Complete REST API for managing parking lot reservations, floors, and slots with JWT authentication</p>
                                            <p><strong>Base URL:</strong> http://localhost:8080</p>
                                        </div>

                                        <div class="auth-section">
                                            <h3>🔐 Authorization</h3>
                                            <p>This API uses Bearer Token (JWT) authentication. Get your token from the login endpoint.</p>
                                            <input type="text" id="authToken" class="auth-input" placeholder="Enter your JWT token here">
                                            <button class="auth-button" onclick="setAuth()">Authorize</button>
                                            <div id="authStatus"></div>
                                        </div>

                                        <!-- Authentication Endpoints -->
                                        <div class="endpoint-group">
                                            <div class="group-header" onclick="toggleGroup(this)">
                                                <h3>🔐 Authentication</h3>
                                                <span class="toggle">▼</span>
                                            </div>
                                            <div class="endpoints">
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method post">POST</span>
                                                        <span class="endpoint-path">/api/auth/signup</span>
                                                        <span class="endpoint-desc">Register a new user</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Register a new user account</p>
                                                        <p><strong>Authentication:</strong> None required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Request Body:</label>
                                                            <textarea id="signup-json" class="json-editor">{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "Password123!",
  "phoneNumber": "9876543210"
}</textarea>
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithCustomJson('signup')">Execute</button>
                                                    </div>
                                                </div>
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method post">POST</span>
                                                        <span class="endpoint-path">/api/auth/login</span>
                                                        <span class="endpoint-desc">Login and get JWT token</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Authenticate user and return JWT token</p>
                                                        <p><strong>Authentication:</strong> None required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Request Body:</label>
                                                            <textarea id="login-json" class="json-editor">{
  "email": "admin@parking.com",
  "password": "Admin@123"
}</textarea>
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithCustomJson('login')">Execute</button>
                                                        <p><strong>Note:</strong> Use the admin credentials above, or register a new user first.</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Floor Endpoints -->
                                        <div class="endpoint-group">
                                            <div class="group-header" onclick="toggleGroup(this)">
                                                <h3>🏢 Floors</h3>
                                                <span class="toggle">▼</span>
                                            </div>
                                            <div class="endpoints">
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method post">POST</span>
                                                        <span class="endpoint-path">/api/floors</span>
                                                        <span class="endpoint-desc">Create a parking floor</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Create a new parking floor</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Request Body:</label>
                                                            <textarea id="createFloor-json" class="json-editor">{
  "name": "Ground Floor",
  "floorNumber": 1
}</textarea>
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithCustomJson('createFloor')">Execute</button>
                                                    </div>
                                                </div>
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method get">GET</span>
                                                        <span class="endpoint-path">/api/floors</span>
                                                        <span class="endpoint-desc">Get all floors</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Retrieve all parking floors</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <button class="try-button" onclick="tryEndpoint('getFloors')">Try it out</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Slot Endpoints -->
                                        <div class="endpoint-group">
                                            <div class="group-header" onclick="toggleGroup(this)">
                                                <h3>🅿️ Slots</h3>
                                                <span class="toggle">▼</span>
                                            </div>
                                            <div class="endpoints">
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method post">POST</span>
                                                        <span class="endpoint-path">/api/slots</span>
                                                        <span class="endpoint-desc">Create parking slots</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Create parking slots for a floor</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Request Body:</label>
                                                            <textarea id="createSlot-json" class="json-editor">{
  "slotNumber": "G001",
  "vehicleType": "FOUR_WHEELER",
  "floorId": 1
}</textarea>
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithCustomJson('createSlot')">Execute</button>
                                                    </div>
                                                </div>
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method get">GET</span>
                                                        <span class="endpoint-path">/api/slots</span>
                                                        <span class="endpoint-desc">Get all slots</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Retrieve all parking slots</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <button class="try-button" onclick="tryEndpoint('getSlots')">Try it out</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Reservation Endpoints -->
                                        <div class="endpoint-group">
                                            <div class="group-header" onclick="toggleGroup(this)">
                                                <h3>📝 Reservations</h3>
                                                <span class="toggle">▼</span>
                                            </div>
                                            <div class="endpoints">
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method post">POST</span>
                                                        <span class="endpoint-path">/api/reserve</span>
                                                        <span class="endpoint-desc">Reserve a slot</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Reserve a slot with cost calculation (₹30/hr for 4-wheeler, ₹20/hr for 2-wheeler)</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Request Body:</label>
                                                            <textarea id="reserve-json" class="json-editor">{
  "slotId": 1,
  "vehicleNumber": "KA05MH1234",
  "startTime": "2025-09-25T10:00:00",
  "endTime": "2025-09-25T12:00:00"
}</textarea>
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithCustomJson('reserve')">Execute</button>
                                                    </div>
                                                </div>
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method get">GET</span>
                                                        <span class="endpoint-path">/api/reservations/{id}</span>
                                                        <span class="endpoint-desc">Get reservation details</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Fetch reservation details by ID</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Reservation ID:</label>
                                                            <input type="number" id="getReservation-id" class="auth-input" value="1" placeholder="Enter reservation ID">
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithParams('getReservation')">Execute</button>
                                                    </div>
                                                </div>
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method delete">DELETE</span>
                                                        <span class="endpoint-path">/api/reservations/{id}</span>
                                                        <span class="endpoint-desc">Cancel reservation</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> Cancel an existing reservation</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Reservation ID:</label>
                                                            <input type="number" id="cancelReservation-id" class="auth-input" value="1" placeholder="Enter reservation ID">
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithParams('cancelReservation')">Execute</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Availability Endpoints -->
                                        <div class="endpoint-group">
                                            <div class="group-header" onclick="toggleGroup(this)">
                                                <h3>🔍 Availability</h3>
                                                <span class="toggle">▼</span>
                                            </div>
                                            <div class="endpoints">
                                                <div class="endpoint">
                                                    <div class="endpoint-header" onclick="toggleEndpoint(this)">
                                                        <span class="method get">GET</span>
                                                        <span class="endpoint-path">/api/availability</span>
                                                        <span class="endpoint-desc">Get available slots</span>
                                                    </div>
                                                    <div class="endpoint-details">
                                                        <p><strong>Description:</strong> List available slots for a given time range</p>
                                                        <p><strong>Authentication:</strong> Bearer Token required</p>
                                                        <div class="json-section">
                                                            <label class="json-label">Start Time:</label>
                                                            <input type="datetime-local" id="getAvailability-startTime" class="auth-input" value="2025-09-25T10:00">
                                                            <label class="json-label">End Time:</label>
                                                            <input type="datetime-local" id="getAvailability-endTime" class="auth-input" value="2025-09-25T12:00">
                                                            <label class="json-label">Floor ID (optional):</label>
                                                            <input type="number" id="getAvailability-floorId" class="auth-input" value="1" placeholder="Enter floor ID (optional)">
                                                        </div>
                                                        <button class="execute-button" onclick="executeWithParams('getAvailability')">Execute</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <script>
                                        let authToken = '';

                                        function toggleGroup(header) {
                                            const endpoints = header.nextElementSibling;
                                            const toggle = header.querySelector('.toggle');

                                            if (endpoints.classList.contains('show')) {
                                                endpoints.classList.remove('show');
                                                toggle.textContent = '▼';
                                            } else {
                                                endpoints.classList.add('show');
                                                toggle.textContent = '▲';
                                            }
                                        }

                                        function toggleEndpoint(header) {
                                            const details = header.nextElementSibling;

                                            if (details.classList.contains('show')) {
                                                details.classList.remove('show');
                                            } else {
                                                details.classList.add('show');
                                            }
                                        }

                                        function setAuth() {
                                            authToken = document.getElementById('authToken').value;
                                            const status = document.getElementById('authStatus');

                                            if (authToken) {
                                                status.innerHTML = '<span class="success">✓ Authorization token set</span>';
                                            } else {
                                                status.innerHTML = '<span class="error">✗ Please enter a valid token</span>';
                                            }
                                        }

                                        function executeWithParams(endpoint) {
            if (endpoint === 'getReservation') {
                const id = document.getElementById('getReservation-id').value;
                if (!id) {
                    alert('Please enter a reservation ID');
                    return;
                }
                const config = {
                    method: 'GET',
                    url: `/api/reservations/${id}`,
                    requiresAuth: true
                };
                executeRequest(config, endpoint);
            } else if (endpoint === 'cancelReservation') {
                const id = document.getElementById('cancelReservation-id').value;
                if (!id) {
                    alert('Please enter a reservation ID');
                    return;
                }
                const config = {
                    method: 'DELETE',
                    url: `/api/reservations/${id}`,
                    requiresAuth: true
                };
                executeRequest(config, endpoint);
            } else if (endpoint === 'getAvailability') {
                const startTime = document.getElementById('getAvailability-startTime').value;
                const endTime = document.getElementById('getAvailability-endTime').value;
                const floorId = document.getElementById('getAvailability-floorId').value;
                
                if (!startTime || !endTime) {
                    alert('Please enter both start time and end time');
                    return;
                }
                
                let url = `/api/availability?startTime=${startTime}:00&endTime=${endTime}:00`;
                if (floorId) {
                    url += `&floorId=${floorId}`;
                }
                
                const config = {
                    method: 'GET',
                    url: url,
                    requiresAuth: true
                };
                executeRequest(config, endpoint);
            }
        }

        function executeWithCustomJson(endpoint) {
                            const jsonTextarea = document.getElementById(endpoint + '-json');
                            if (!jsonTextarea) {
                                alert('JSON editor not found for this endpoint');
                                return;
                            }

                            try {
                                const customJson = JSON.parse(jsonTextarea.value);
                                const endpointConfigs = {
                                    signup: {
                                        method: 'POST',
                                        url: '/api/auth/signup',
                                        body: customJson
                                    },
                                    login: {
                                        method: 'POST',
                                        url: '/api/auth/login',
                                        body: customJson
                                    },
                                    createFloor: {
                                        method: 'POST',
                                        url: '/api/floors',
                                        body: customJson,
                                        requiresAuth: true
                                    },
                                    createSlot: {
                                        method: 'POST',
                                        url: '/api/slots',
                                        body: customJson,
                                        requiresAuth: true
                                    },
                                    reserve: {
                                        method: 'POST',
                                        url: '/api/reserve',
                                        body: customJson,
                                        requiresAuth: true
                                    }
                                };

                                const config = endpointConfigs[endpoint];
                                if (!config) {
                                    alert('Endpoint configuration not found');
                                    return;
                                }

                                if (config.requiresAuth && !authToken) {
                                    alert('Please set your authorization token first!');
                                    return;
                                }

                                executeRequest(config, endpoint);
                            } catch (error) {
                                alert('Invalid JSON format: ' + error.message);
                            }
                        }

                        function tryEndpoint(endpoint) {
                                            const endpointConfigs = {
                                                signup: {
                                                    method: 'POST',
                                                    url: '/api/auth/signup',
                                                    body: {
                                                        firstName: 'John',
                                                        lastName: 'Doe',
                                                        email: 'john.doe@example.com',
                                                        password: 'Password123!',
                                                        phoneNumber: '9876543210'
                                                    }
                                                },
                                                login: {
                                                    method: 'POST',
                                                    url: '/api/auth/login',
                                                    body: {
                                                        email: 'admin@parking.com',
                                                        password: 'Admin@123'
                                                    }
                                                },
                                                createFloor: {
                                                    method: 'POST',
                                                    url: '/api/floors',
                                                    body: {
                                                        name: 'Ground Floor',
                                                        floorNumber: 1
                                                    },
                                                    requiresAuth: true
                                                },
                                                getFloors: {
                                                    method: 'GET',
                                                    url: '/api/floors',
                                                    requiresAuth: true
                                                },
                                                createSlot: {
                                                    method: 'POST',
                                                    url: '/api/slots',
                                                    body: {
                                                        slotNumber: 'G001',
                                                        vehicleType: 'FOUR_WHEELER',
                                                        floorId: 1
                                                    },
                                                    requiresAuth: true
                                                },
                                                getSlots: {
                                                    method: 'GET',
                                                    url: '/api/slots',
                                                    requiresAuth: true
                                                },
                                                reserve: {
                                                    method: 'POST',
                                                    url: '/api/reserve',
                                                    body: {
                                                        slotId: 1,
                                                        vehicleNumber: 'KA05MH1234',
                                                        startTime: '2025-09-25T10:00:00',
                                                        endTime: '2025-09-25T12:00:00'
                                                    },
                                                    requiresAuth: true
                                                },
                                                getReservation: {
                                                    method: 'GET',
                                                    url: '/api/reservations/1',
                                                    requiresAuth: true
                                                },
                                                cancelReservation: {
                                                    method: 'DELETE',
                                                    url: '/api/reservations/1',
                                                    requiresAuth: true
                                                },
                                                getAvailability: {
                                                    method: 'GET',
                                                    url: '/api/availability?startTime=2025-09-25T10:00:00&endTime=2025-09-25T12:00:00&floorId=1',
                                                    requiresAuth: true
                                                }
                                            };

                                            const config = endpointConfigs[endpoint];
                                            if (!config) return;

                                            if (config.requiresAuth && !authToken) {
                                                alert('Please set your authorization token first!');
                                                return;
                                            }

                                            executeRequest(config, endpoint);
                                        }

                                        async function executeRequest(config, endpointName) {
                                            const resultDiv = document.getElementById('result-' + endpointName) || createResultDiv(endpointName);
                                            resultDiv.innerHTML = '<div style="color: #666;">Executing request...</div>';

                                            try {
                                                const headers = {
                                                    'Content-Type': 'application/json'
                                                };

                                                if (config.requiresAuth && authToken) {
                                                    headers['Authorization'] = 'Bearer ' + authToken;
                                                }

                                                const requestOptions = {
                                                    method: config.method,
                                                    headers: headers
                                                };

                                                if (config.body) {
                                                    requestOptions.body = JSON.stringify(config.body);
                                                }

                                                const response = await fetch(config.url, requestOptions);
                                                const responseText = await response.text();

                                                let responseData;
                                                try {
                                                    responseData = JSON.parse(responseText);
                                                } catch (e) {
                                                    responseData = responseText;
                                                }

                                                const statusClass = response.ok ? 'success' : 'error';
                                                const statusText = response.ok ? 'SUCCESS' : 'ERROR';

                                                resultDiv.innerHTML = `
                                                    <div style="margin-bottom: 10px;">
                                                        <strong>Status:</strong> <span class="${statusClass}">${response.status} ${statusText}</span>
                                                    </div>
                                                    <div style="margin-bottom: 10px;">
                                                        <strong>Response:</strong>
                                                    </div>
                                                    <div class="example-box" style="margin: 0; white-space: pre-wrap;">
                                ${JSON.stringify(responseData, null, 2)}
                                                    </div>
                                                `;

                                                // Auto-save JWT token from login response
                                                if (endpointName === 'login' && response.ok && responseData.token) {
                                                    document.getElementById('authToken').value = responseData.token;
                                                    authToken = responseData.token;
                                                    document.getElementById('authStatus').innerHTML = '<span class="success">✓ Token auto-saved from login response</span>';
                                                }

                                            } catch (error) {
                                                resultDiv.innerHTML = `
                                                    <div class="error">
                                                        <strong>Error:</strong> ${error.message}
                                                    </div>
                                                `;
                                            }
                                        }

                                        function createResultDiv(endpointName) {
                                            const resultDiv = document.createElement('div');
                                            resultDiv.id = 'result-' + endpointName;
                                            resultDiv.style.marginTop = '15px';
                                            resultDiv.style.padding = '15px';
                                            resultDiv.style.background = '#f8f9fa';
                                            resultDiv.style.borderRadius = '4px';
                                            resultDiv.style.border = '1px solid #e9ecef';

                                            // Find the endpoint details div and append the result
                                            const endpointDetails = event.target.closest('.endpoint-details');
                                            if (endpointDetails) {
                                                endpointDetails.appendChild(resultDiv);
                                            }

                                            return resultDiv;
                                        }

                                        // Auto-expand first group
                                        document.querySelector('.endpoints').classList.add('show');
                                        document.querySelector('.toggle').textContent = '▲';
                                    </script>
                                </body>
                                </html>
                                                """;
    }
}